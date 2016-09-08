# Ember CLI Gradle Plugin
---------

A plugin for building Ember CLI projects with gradle.

The plugin expects that you have a global install of npm, and that bower and ember-cli are installed as NPM dependencies of your project.

Note: This plugin uses the Distribution plugin under the hood to create its zip artifact, so it will inherit tasks from that plugin.

This plugin's task structure mimis that of the Gradle Java Plugin. The `build` task built into gradle depends on `check` and `assemble`. `check` is responsible for running tests, and `assemble is responsible for creating the build artifacts.

## Requirements
---------

 - As of version 2.0.0, you must have at least ember-cli version 1.13.6 to have parallel builds work correctly. This is due to a breaking change that occurred in [Ember CLI](https://github.com/ember-cli/ember-cli/pull/3239).
 - A build task is necessary for this plugin to work. If you use Gradle 2.4 or later, you get this task for free as part of the default build lifecycle
 - Java 8 or later
 - npm -g install phantomjs 
    + this is needed to run the tests locally
 - brew install watchman
    + needed on Mac for ember to run correctly

## Usage
---------

In your build.gradle file...

     buildscript {
         repositories {
             jcenter()
         }
         dependencies {
             classpath "com.kiefer.gradle:ember-cli-gradle-plugin:VERSION_NUMBER"
         }
     }

     apply plugin: "com.kiefer.gradle.embercli"

Then run:

     ./gradlew clean build

If you need to configure the plugin beyond its default settings, you can do so with the following options.

     apply plugin: "com.kiefer.gradle.embercli"

     embercli {
          // toggle the build environment
          environment = "development"

          // point to a different npm registry
          npmRegistry = "https://my-custom-npm-registry.com"

          // use only the presence of the node modules folder to determine up to date status, not all its contents
          trackNodeModulesContents = false

          // use only the presence of the bower components folder to determine up to date status, not all its contents
          trackBowerComponentsContents = false
     }

For a more complete example including extraction into a project, check out my [sample project](https://github.com/benkiefer/gradle-ember-cli-example).

## The Clean Task
---------

An independent task that removes the `dist` and `tmp` directories.

Example:

     ./gradlew clean

## The NpmInstall Task
---------

Shells out to npm and executes the install command.

Example:

     ./gradlew npmInstall

## The BowerInstall Task
---------

Executes the bower install command. Also executes the NpmInstall task to ensure bower is available.

Example:

     ./gradlew bowerInstall

## The BowerUpdate Task
---------

Executes the bower update command. Also executes the NpmInstall task to ensure bower is available and the Bower Install task.

Example:

     ./gradlew bowerUpdate

## The Test Task
---------

Shells out to Ember CLI and executes the test command. Also executes the BowerInstall and NpmInstall tasks. The `check` task depends on `test`.

Example:

     ./gradlew test

## The EmberBuild Task
---------

Shells out to Ember CLI and executes the build command with a production environment target. Also executes the Test, NpmInstall, and BowerInstall task. The `assemble` task depends on `emberBuild`.

Example:

     ./gradlew emberBuild