#!/bin/sh
rm -r FinalProject
java -jar sablecc.jar FinalProject.js
javac SymbolTE.java
mv SymbolTE.class FinalProject
javac Variable.java
mv Variable.class FinalProject
javac Method.java
mv Method.class FinalProject
javac SymbolTable.java
mv SymbolTable.class FinalProject
javac PrintTree.java
mv PrintTree.class FinalProject
javac SymbolTraverse.java
mv SymbolTraverse.class FinalProject
javac CheckType.java
mv CheckType.class FinalProject
javac Translate.java
mv Translate.class FinalProject
javac -d . Main.java
java FinalProject.Main < prog1.txt > prog1.s
java FinalProject.Main < prog2.txt > prog2.s
java FinalProject.Main < prog3.txt > prog3.s
