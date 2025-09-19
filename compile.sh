#!/usr/bin/env bash
set -e
echo "Compiling CCRM project..."
mkdir -p out
# find .java files and compile
find src -name "*.java" > sources.txt
javac -d out @sources.txt
echo "Compiled. Run with: java -cp out edu.ccrm.cli.Main"
