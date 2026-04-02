#!/bin/bash
set -e

echo "Publishing Cucumber Report to GitHub Pages..."

CURRENT_BRANCH=$(git branch --show-current)
echo "Current branch: $CURRENT_BRANCH"

if [ ! -d "cucumber-report/cucumber-html-reports" ]; then
    echo "No report found. Run tests first: mvn clean verify"
    exit 1
fi

cp -r cucumber-report/cucumber-html-reports /tmp/cucumber-html-reports
echo "Report copied to temp location"

git fetch origin gh-pages
git checkout gh-pages
git pull origin gh-pages

cp -rf /tmp/cucumber-html-reports/. ./
cp -r mock-app ./mock-app

cat > index.html << 'HTML'
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><meta http-equiv="refresh" content="0;url=overview-features.html"></head>
<body><p>Redirecting to CoreSuite Test Report...</p></body></html>
HTML

git add -A
git commit -m "Update Cucumber test report - $(date +%Y-%m-%d-%H-%M)" || echo "No changes"
git push origin gh-pages

git checkout "$CURRENT_BRANCH"
echo "Report published."
echo "View at: https://pinolopez.github.io/java/overview-features.html"