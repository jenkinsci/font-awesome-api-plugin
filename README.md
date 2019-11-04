# Font Awesome Jenkins Plugin

[![Jenkins Version](https://img.shields.io/badge/Jenkins-2.138.4-green.svg?label=min.%20Jenkins)](https://jenkins.io/download/)
![JDK8](https://img.shields.io/badge/jdk-8-yellow.svg?label=min.%20JDK)
[![License: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![GitHub Actions](https://github.com/uhafner/bootstrap4-api-plugin/workflows/GitHub%20Actions/badge.svg)](https://github.com/uhafner/font-awesome-api-plugin/actions)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/uhafner/bootstrap4-api-plugin.svg)](https://github.com/uhafner/font-awesome-api-plugin/pulls)

Provides [Font Awesome](https://fontawesome.com) for Jenkins Plugins.

This plugin contains the latest [WebJars](https://www.webjars.org) release and corresponding Jenkins UI elements. 

## How to use the plugin

In order to use this JS library, add a maven dependency to your pom:
```xml
    <dependency>
      <groupId>io.jenkins.plugins</groupId>
      <artifactId>font-awesome-api</artifactId>
      <version>[latest version]</version>
    </dependency>
```

Then you can use Font Awesome in your jelly files using the following snippet:
```xml
      <st:adjunct includes="io.jenkins.plugins.font-awesome"/>
```
 
You can find several examples of Jenkins views that use Font Awesome in the 
[Warnings Next Generation plugin](https://github.com/jenkinsci/warnings-ng-plugin).

