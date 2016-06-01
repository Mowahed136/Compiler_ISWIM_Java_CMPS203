#! /bin/bash

javac -d . ./evaluator/ast/*.java
javac -cp . -d . ./evaluator/cek/*.java
javac -cp . -d . ./evaluator/*.java
javac -cp . -d . ./evaluator/test/*.java
javac -cp . -d . ./front_end/*.java
javac -cp . -d . ./*.java

java ParserRunner < test_input.txt > temp
