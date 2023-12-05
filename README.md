# FOREST SERVICE
### Author- Esther Nyang'au Onyando.

## Description
Wildlife Tracker is an application that allows rangers to keep track various animal(common and endangered) that they come across.
It allows one to add ,view and delete information on rangers,animals and sightings.
## Table of contents

* [Demo](#demo)

* [Technologies](#technologies)

* [Known Bugs](#knownbugs)

* [Setup](#setup)

* [BDD ](#BDD)

* [Contact](#contact)

* [Licence](#Licence)


## Demo
Link to github:https://github.com/estheronyando/ForestServiceApp.git

## Technologies

1. Java
1. Junit
1. Maven
1. Spark
1. Handlebars
1. CSS





## Known Bugs
There are curently no known bugs.
## Setup
### Prerequisites
You will need to have
1. Open JDK 20
1. SDK
1. Maven
1. Junit
1. Spark
1. Intellij


### Cloning
* you can clone it directly to your folder:

```
$ https://github.com/estheronyando/ForestServiceApp.git

```
* Alternatively,you can download  manually and pass it to your root directory.

* Extract Files

    * Open the file with inteliiJ
    * Navigate to your terminal
    * cd IdeaProjects/ForestService

### Database Setup
* change data in files {src/main/java/DB} and {src/test/java/DatabaseRule} to postgres database access information that you use locally on your machine.
* PSQL statements to use :
    * CREATE DATABASE wildlife_tracker;
    * CREATE TABLE rangers(id serial PRIMARY KEY,name VARCHAR,badge_number VARCHAR,phone_number VARCHAR,email VARCHAR);

    * CREATE TABLE sightings(id serial PRIMARY KEY,animal_name VARCHAR,ranger_id INT,location_id VARCHAR,sight_time VARCHAR);
    * CREATE TABLE animals(id serial PRIMARY KEY, name varchar,type VARCHAR,health VARCHAR,age VARCHAR);

    * CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;

### Run Locally
Run command:

```
$ maven

```


## Contact
In case of any question or contributions, contact me at esthermariachana@gmail.com


## Licence
MIT License

Copyright (c) 2023 Esther Onyando

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.



