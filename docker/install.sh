#!/bin/bash

# Docker
sudo apt remove --yes docker docker-engine docker.io containerd runc \
    && sudo apt --yes update \
    && sudo apt --yes install \
        apt-transport-https \
        ca-certificates \
        curl \
        software-properties-common \
    && wget --quiet --output-document=- https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - \
    && sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable" \
    && sudo apt --yes update \
    && sudo apt --yes --no-install-recommends install docker-ce \
    && sudo usermod --append --groups docker "$USER" \
    && sudo systemctl enable docker \
    && printf '\nDocker installed successfully\n\n'

printf 'Waiting for Docker to start...\n\n'
sleep 3

# Docker compose
sudo wget \
        --output-document=/usr/local/bin/docker-compose \
        https://github.com/docker/compose/releases/download/1.25.5/run.sh \
    && sudo chmod +x /usr/local/bin/docker-compose \
    && sudo wget \
        --output-document=/etc/bash_completion.d/docker-compose \
        "https://raw.githubusercontent.com/docker/compose/$(docker-compose version --short)/contrib/completion/bash/docker-compose" \
    && printf '\nDocker Compose installed successfully\n\n'

