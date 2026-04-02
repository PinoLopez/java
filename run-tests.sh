#!/bin/bash
set -e

echo "========================================"
echo "CoreSuite Test Automation Suite"
echo "========================================"
echo ""

echo "Step 1/3: Running Cucumber Tests..."
echo "----------------------------------------"
mvn clean verify -DbaseUrl=https://pinolopez.github.io/java/mock-app

if [ $? -ne 0 ]; then
    echo "Tests failed. Check the output above."
    exit 1
fi

echo ""
echo "Tests passed."
echo ""

echo "Step 2/3: Publishing Report to GitHub Pages..."
echo "----------------------------------------"
./publish-report.sh

echo ""
echo "Step 3/3: Opening Report in Browser..."
echo "----------------------------------------"
echo "Waiting 30 seconds for GitHub Pages CDN..."
sleep 30

firefox --new-window "https://pinolopez.github.io/java/overview-features.html?nocache=$(date +%s)" &

echo ""
echo "========================================"
echo "ALL DONE."
echo "========================================"
echo "Report URL: https://pinolopez.github.io/java/overview-features.html"
echo "Opened at: $(date)"
echo ""