#!/usr/bin/env bash

sudo apt-get update

yes | sudo apt-get install python-pip python-dev build-essential python-setuptools
sudo pip install --upgrade pip 
sudo pip install --upgrade virtualenv 
sudo pip install paramiko PyYAML Jinja2 httplib2 six

if [ $(dpkg-query -W -f='${Status}' ansible 2>/dev/null | grep -c "ok installed") -eq 0 ];
then
    echo "Add APT repositories"
    export DEBIAN_FRONTEND=noninteractive
    apt-get install -qq software-properties-common &> /dev/null || exit 1
    apt-add-repository ppa:ansible/ansible &> /dev/null || exit 1

    apt-get update -qq

    echo "Installing Ansible"
    apt-get install -qq ansible &> /dev/null || exit 1
    echo "Ansible installed"
fi

sudo apt-get install unzip

echo "Running Ansible"
bash -c "ansible-playbook /vagrant/playbook.yml --connection=local"
