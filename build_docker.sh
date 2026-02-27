#!/bin/bash
NAMESPACE="${1:-codebase_b1106_app}"
docker build -t "$NAMESPACE" .