## Template Repository
  ##### There will be 3 projects in a connector repository.
  - 1 parent project and 2 subprojects.
  - one Subproject should be created for connector provider and one for providerExt.
   

  ##### Parent project
  
  - .github folder contains workflows. These workflows should be configured to run on pull request to master. All yml files under this folder will be executed based on condition. 
  <br><br>start should contain below lines.
           
           
            on:
              pull_request:
                branches: [ master ]     
   <br>yml file should have below commands at the end to publish the project to Github build snapshot.
   
            - name: Build with Gradle
                  env:
                    GH_SECRET_TOKEN: ${{ secrets.GH_SECRET_TOKEN }}
                    GH_RUN_NUMBER: ${{ github.run_number }}
                    #GH_REPO_SLUG: ${{github.GITHUB_REPOSITORY_NAME_SLUG}}
                    GH_REPO_NAME: ${{github.repository}}
            
                  run: |
                    gradle :WmTemplateProviderExt:assembleArtifact
                    gradle :WmTemplateProvider:assembleArtifact
                    gradle publish
   <br>
   
   - For any connector related documentation use "documentation" folder in parent project.
   - Test assets should be stored inside "test" folder.
   - gradle.properties should be used for all properties related to build.
   <br><br>
     
   Remember to update below properties after cloning this Template package. 
    
            repo.name=system-connector-template
            build.version.major=10
            build.version.minor=5
            build.version.micro=0
            provider.package.name=WmTemplateProvider
            providerext.package.name=WmTemplateProviderExt
    
            
   - use ".gitignore" to ignore any files/folder you may not want to commit.
   <br><nbsp> example- build, .gradle, secrets.properties, .project, .idea
   
   - secrets.properties should be used to store secret tokens. SO PLEASE REMEMBER TO ADD secrets.properties TO .gitignore
   - settings.gradle contains root project and subproject names.
  
  <br><br>
  #### Sub Projects
  
  - Parent project contains two subprojects under the folder 'packages'.  
  - Both child projects contain build.gradle and settings.gradle.
  - Developer should update these build.gradle files to add dependency required for the connector.
  - Be cautious while adding a dependency. Please do not add redundant dependency to the project.
  
  ### Steps to be followed
  
  1. Use this template repository to create repo for your connector.
  2. Create develop and feature(it should be id of itrac you are working on) branch from master.
  3. Clone feature branch in your local.
  4. Now we need to remove secrets.properties file from GitHub. To do this take a backup of this file and delete from the project.
  5. Commit and push your changes to feature.
  6. Add secrets.properties back to the project. Make sure secrets.properties is added to .gitignore. Add your secret token to this file.
  7. Now open ".github/workflows/gradle.yml" file and update below commands with respective connector names. <br>
            
            gradle :WmTemplateProviderExt:assembleArtifact
            gradle :WmTemplateProvider:assembleArtifact

  8. Go inside packages and rename the two folder accordingly.
  9. Please do not delete build.gradle and settings.gradle in any of the folders.
  10. build.gradle in subProject should be updated if need to add any dependency.
  11. Now open gradle.properties and update below properties accordingly.<br>
  
            repo.name=connector-template
            build.version.major=10
            build.version.minor=5
            build.version.micro=0
            provider.package.name=WmTemplateProvider
            providerext.package.name=WmTemplateProviderExt

  12. Open settings.gradle and update accordingly.
  13. Run below commands to generate package zip locally.<br>
  
            gradle :WmTemplateProviderExt:assembleArtifact
            gradle :WmTemplateProvider:assembleArtifact          
  14. You will find zip in "build/tmpzip" folder.
  15. Once changes are merged to master GitHub Actions workflow will be invoked and packages will be published to "System-Build-Snapshot".
  16. Always use feature brach for development. Merge your changes from feature to develop branch. After testing raise pull request to master. A reviewer needs to review and merge the changes.
  
  