# report-generator-service
# Introduction
This project contains report generator specific logic , mainly working with CSV as input and output. Also It provides supoort for XLS, XML type file also.
It uses Spring Schedular to schedule the report generate API at every day at 1AM. In it , it then stores the files at temp location. and then after runnning other API , We can download it from temp location.

# Getting Started
Tools required for mortgage application:
1. JDK17
2. Gradle
3. Intellij
