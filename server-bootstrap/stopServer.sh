#!/bin/bash

. ./gf.config

echo "Stopping server and locator..."
gfsh run --file=serverStop.gf
