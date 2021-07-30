@ECHO OFF
:: This batch file details Windows 10, hardware, and networking configuration.
TITLE Auto generate configs and files
ECHO Please wait... Generating files

:: Read properties file
@echo off
For /F "tokens=1* delims==" %%A IN (gradle.properties) DO (
    set %%A=%%B
::    IF "%%A"=="ELECTRON_WIN_VERSION_NUMBER" set ELECTRON_WIN_VERSION_NUMBER=%%B
::    IF "%%A"=="ELECTRON_WIN_BUILD_NUMBER" set ELECTRON_WIN_BUILD_NUMBER=%%B
    echo %%A, %%B
)

del secrets.properties
echo build.dependency.organization.token=secrettoken>> secrets.properties
echo build.snapshot.organization.token=secrettoken>> secrets.properties

::
ECHO Updating settings.gradle......

powershell -Command "(gc settings.gradle) -replace 'connector-template', '%repo.name%' -replace '#WmTemplateProvider#','%provider.package.name%' -replace '#WmTemplateProviderExt#','%providerext.package.name%'  | Out-File -encoding ASCII settings.gradle"
ECHO Renaming package names.....
cd packages
RENAME WmTemplateProvider %provider.package.name%
RENAME WmTemplateProviderExt %providerext.package.name%
cd ..
ECHO Generating gradle.properties for promote project........
cd gradle\promote
del gradle.properties


(
  echo #This is an auto generated file.....
  echo repo.name=%repo.name%
  echo build.version.major=%build.version.major%
  echo build.version.minor=%build.version.minor%
  echo build.version.micro=%build.version.micro%
  echo provider.package.name=%provider.package.name%
  echo providerext.package.name=%providerext.package.name%
  echo bas.provider.name=%bas.provider.name%
  echo bas.provider.build.name=%bas.provider.build.name%
  echo bas.providerext.build.name=%bas.providerext.build.name%
) > gradle.properties

cd ..\..
cd .github/workflows
ECHO Updating gradle.yml......
powershell -Command "(gc gradle.yml) -replace '#WmTemplateProvider#','%provider.package.name%' -replace '#WmTemplateProviderExt#','%providerext.package.name%'  | Out-File -encoding ASCII gradle.yml"
ECHO Updating provider-validation.yml......
powershell -Command "(gc provider-validation.yml) -replace 'system-connector-template','%repo.name%'  | Out-File -encoding ASCII provider-validation.yml"
ECHO Auto generation completed....
cd ..\..


::PAUSE