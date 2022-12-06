# Week 5 - Dask
` conda create -n testingDask python=3.8`

` conda install dask`

## HDFS and Yarn commands
`jps` to see the resource manager
`stop-all.sh` stop all hdfs and yarn stuff
`start-all.sh` restart hadoop cluster
`hdfs namenode -format` to format the node
`start-dfs.sh` to start the HDFS

## Packages
Can provide a file to conda to install all of the packages. The file contains the desired packages
```
dask
numpy
pandas
dask-yarn
conda-pack
venv-pack
skein
asyncssh
```
Packages to install with pip:
```
venv-pack
asyncssh
```

Then with your desired environemt activated with `conda activate <environment_name> `simply run `conda install --file filename.txt`


## Dask Cluster
[COnfiguring a Distributed Dask Cluster Guide](https://blog.dask.org/2020/07/30/beginners-config)

* Cluster always on
* Cluster spins up automatically, and changes size based on python API 

## Skein
Skein uses python API calls to submit applications to the YARN cluster. Following is a summary of this [guide](https://jcristharif.com/skein/quickstart.html)


Start skein driver: `skein driver start`

Write a simple application:
```yaml
name: hello_world
queue: default

master:
  resources:
    vcores: 1
    memory: 512 MiB
  script: |
    sleep 60
    echo "Hello World!"
```
Here the application will deploy to the `default` YARN queue.

To run it run: `skein application submit hello_world.yaml`

You will get back an output of the application ID

To check the status of all running applications run: `skein application ls`

To stop the driver use `skein driver stop`

### Packaging environments and using them

To package a conda environment to be distributed with our application:
`conda activate <environment_name>`

Then to pack the currently activated environment:
`conda pack -o <environment_name.tar.gz>`

To use the packaged environment you need to include the archive in `files`:
```yaml
services:
    my_services:
        files:
            #The environment archie will be uploaded to HDFS and extracted into a directory named 'environment' in each container
            environment: environment_name.tar.gz
        script: |
            #Activate the environment
            source environment/bin/activate
            
            #Run commands inside the environment. All executables or imported python libraries will be from within the packaged environment.
            my-cool-application
```

## Pros and Cons of Dask

### Guides
#### [Official Dask tutorial](https://www.dask.org/get-started)

### Troubleshooting
