## Connector Repository
  
  This repository is created by cloing or importing the template repository and used for generating the Connector artifacts and building the Connector package.

  The repository consists of the following folders and files:

#### .github/workflows:
The **.github** folder contains workflows. These workflows should be configured to run on pull request to the configured branch. All yml files under this folder will be executed based on condition.

**Example:**

                on:
                  pull_request:
                    branches: [ master ]     


- **gradle.yml:**: This yml file consists of build jobs to run the gradle build commands for building the Connector using Gradle upon pushing the changes to the configured branch.

**Example:**

              run: |
                ./gradlew :WmTemplateProvider:assembleArtifact


#### packages:
The packages folder consists of the templates for the Connector packages. The templates need to be replaced with the contents of the Connector packages. Under the template package folder, the below two files required for the building the Connector package using Gradle.

- **build.gradle:** This file consists of the tasks defined and the dependencies mentioned which are required to build the Connector package.


#### gradle.properties:
This file consists of all the properties used for building the Connector, build dependencies and build package repositories.

After the Connector repository is created from the template repository, below properties should be updated accordingly.

            repo.name=connector-repository
            build.version.major=10
            build.version.minor=5
            build.version.micro=0
            provider.package.name=WmTemplateProvider
            build.dependency.organization=your-organization-name
            build.dependency.reponame=connector-template-repository
            build.snapshot.organization=your-organization-name
            build.snapshot.reponame=connector-repository


#### generateArtifacts.bat:
This is the automation script that can generate the artifacts based on the values provided in the gradle.properties file.

#### build.gradle:
This file present in the root folder of the template repository consists of the tasks defined to publish the Connector package to the repository specified using the Package Manager.

#### settings.gradle:
In this settings.gradle, the Connector packages are defined for the respective Connector repository.


*For creating the Connector repository and building the Connector, refer to the [Connector Developer Program](https://open-source.softwareag.com/Connector-Developer-Program/cloudstreams-cdk/2-create-new-repo).*

------------------------------

These tools are provided as-is and without warranty or support. They do not constitute part of the webMethods product suite. Users are free to use, fork and modify them, subject to the license agreement. While we welcome contributions, we cannot guarantee to include every contribution in the master project.
