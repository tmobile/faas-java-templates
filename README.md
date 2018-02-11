# Java Templates for OpenFaas

This repository includes Vertx and SpringBoot Java Templates that can be used to write your serverless functions for OpenFaas platform. If you are new to OpenFaas watch Alex's Kubecon [talk](https://www.youtube.com/watch?v=XgsxqHQvMnM) Zero to Serverless in 60 seconds.

## Downloading the template
Before you can create serverless functions using the above the templates, you must first install them on your local machine. Run the command below to install these templates on your local machine.
Additional requirement is you need to have Faas cli installed and configured to either work with your local docker swarm or kubernetes cluster or remote clusters.

```sh

faas template pull git@github.com:tmobile/faas-java-templates.git

```

Verify templates are installed locally using command below

```sh

faas new --list

```

## Creating functions using these templates

As mentioned earlier repository currently provides both Vertx or SpringBoot templates. 

* To create a function using these templates, run the command below

```sh

faas new {name of function} --lang vertx|springboot

```

## Building the function

Once you've implemented the function logic, you would build the function using the faas cli build command as shown below

```sh

faas build {stack yml} --image {function docker image} --handler {path to your function handler} --lang vert|springboot --name {function name}

```

## Push the Image to a Registry
Once your function image is built you can push the image to a docker registry using the faas cli.

```sh

faas push -f {stack yml}

```

## Deploying the function
Once the function is built using the faas cli, you can simply deploy them as shown below

```sh

faas deploy --image {function docker image} --name {function name}

```


