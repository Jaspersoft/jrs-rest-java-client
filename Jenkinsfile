#!groovy
//JRS REST clients

pipeline {
  agent { label 'qaa' }
  options {
    timeout(time: 1, unit: 'HOURS')
    buildDiscarder(logRotator(artifactNumToKeepStr: '5'))
    timestamps()
  }
  environment {
    PROD_MVNREPO_SERVER_ID = 'jaspersoft-jfrog-io'
    PROD_SNAPSHOT_REPO = 'jaspersoft-clients-snapshots'
    PROD_PROD_REPO = 'jaspersoft-clients-releases'
    
    DEV_MVNREPO_SERVER_ID = 'mvnrepo-jaspersoft-com'
    DEV_SNAPSHOT_REPO = 'rest-client-test'
    DEV_PROD_REPO = 'rest-client-test-prod'
    
    RESOLVE_REPO_ID = "${env.DEV_MVNREPO_SERVER_ID}"
    RESOLVE_REPO = 'repo'

    devBuild = JENKINS_URL.contains('-dev')

    MVNREPO_SERVER_ID = "${devBuild ? env.DEV_MVNREPO_SERVER_ID : env.PROD_MVNREPO_SERVER_ID}"
    SNAPSHOT_REPO = "${devBuild ? env.DEV_SNAPSHOT_REPO : env.PROD_SNAPSHOT_REPO}"
    PROD_REPO = "${devBuild ? env.DEV_PROD_REPO : env.PROD_PROD_REPO}"

    MVN_NAME = 'mvn-default'
    JDK_NAME = 'jdk-default'

  }
  tools {
    maven env.MVN_NAME
    jdk env.JDK_NAME
  }
  stages {
    stage('Configure') {
      steps {
        rtMavenResolver (
          id: "maven-resolver-${env.JOB_BASE_NAME}",
          serverId: "${env.RESOLVE_REPO_ID}",
          releaseRepo: "${env.RESOLVE_REPO}",
          snapshotRepo: "${env.RESOLVE_REPO}",
        )  
        rtMavenDeployer (
          id: "maven-deployer-${env.JOB_BASE_NAME}",
          serverId: "${env.MVNREPO_SERVER_ID}",
          releaseRepo: "${env.PROD_REPO}",
          snapshotRepo: "${env.SNAPSHOT_REPO}",
        )
        rtBuildInfo (
          captureEnv: true,
        )
      }
    }
    stage('Build'){
      steps {
        rtMavenRun (
          pom: 'pom.xml',
          goals: "clean install source:jar --update-snapshots -Dmaven.repo.local=${env.WORKSPACE}/.m2/repository --batch-mode",
          /* Not working, bug recently fixed and not yet released. See https://github.com/jfrog/jenkins-artifactory-plugin/pull/139 and HAP-1183
            opts: "--update-snapshots -Dmaven.repo.local=${env.WORKSPACE}/.m2/repository --batch-mode",
          */ 
          resolverId: "maven-resolver-${env.JOB_BASE_NAME}",
          deployerId: "maven-deployer-${env.JOB_BASE_NAME}",
        )
      }
    }
  }
  post {
    success {
      archiveArtifacts allowEmptyArchive: true, artifacts: '**/target/*.jar'
      script {
        echo "devBuid: ${env.devBuild}, server: ${env.MVNREPO_SERVER_ID}, snapshots: ${env.SNAPSHOT_REPO}, release: ${env.PROD_REPO}, JENKINS_URL: ${JENKINS_URL}"
        step([$class: 'Publisher', reportFilenamePattern: '**/target/*-reports/*.xml' ])
      }
      rtPublishBuildInfo (
        serverId: "${env.MVNREPO_SERVER_ID}",
      )
      rtAddInteractivePromotion (
        serverId: "${env.MVNREPO_SERVER_ID}",
        targetRepo: "${env.PROD_REPO}",
        sourceRepo: "${env.SNAPSHOT_REPO}",
        copy: true,
      )
    }
    cleanup {
      cleanWs()
    }
  }
}
