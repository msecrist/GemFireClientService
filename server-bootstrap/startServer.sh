#!/bin/bash

. ./gf.config

echo "Starting locator and server..."
gfsh run --file=serverStart.gf

echo "Loading data..."
gfsh run --file=loadData.gf
