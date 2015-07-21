#!/bin/sh

version=$(grep "ENV AGENT_BOND_VERSION" Dockerfile | awk '{ print $3 }')
docker build -t fabric8/java-agent-bond:$version .
docker tag --force fabric8/java-agent-bond:$version fabric8/java-agent-bond:latest 
