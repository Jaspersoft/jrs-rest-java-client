# JRS java REST client build setup via pipeline

## Builds
Every branch with exisiting `Jenkisfile` will be picked up by CI and built
Exceptions:
* branches containing `dev-` in names are built on development CI
* branches containing `dev-` in names are **not** built on production CI

## Pipeline
`Jenkinsfile` uses [Declarative Pipeline](
https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline) 
syntax. 
`rtMavenResolver`, `rtMavenDeployer`, `rtBuildInfo`, `rtMavenRun`, `rtPublishBuildInfo` and 
`rtAddInteractivePromotion` are declarative steps for [Jenkins Artifactory Plugin](
https://github.com/JFrog/jenkins-artifactory-plugin) and are documented in 
[Working With Pipeline Jobs in Jenkins](https://www.jfrog.com/confluence/display/RTF/Working+With+Pipeline+Jobs+in+Jenkins)

## Flowchart
```mermaid
graph TB
  subgraph agent 'qaa'
    subgraph pipeline options, environment and tools
      A1[Setup pipeline]
    end
    subgraph stages
      subgraph stage Configure
        A1 --> B1["Configure (artifactory pipeline plugin)"]
      end
      subgraph stage Build
        B1 --> B2["Build (run Maven via artifactory pipeline plugin)"]
      end
    end
   subgraph Post-build
    subgraph success
      B2 --> C2[archive artifacts]
      C2 --> C3[publish test results]
      C3 --> C4[Set buildInfo and interactive promotion artifactory plugin]
    end
    A1 --> |Timeout or Error | E2
    subgraph always
      E2[Remove workspace]
    end
    C4 --> E2
  end
end
```
