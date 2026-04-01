#!/bin/bash
set -e

echo "📤 Publishing Cucumber Report to GitHub Pages..."

# Save current branch
CURRENT_BRANCH=$(git branch --show-current)
echo "Current branch: $CURRENT_BRANCH"

# Ensure report exists
if [ ! -f "target/cucumber-report.html" ]; then
    echo "❌ No report found. Run tests first: mvn clean verify"
    exit 1
fi

# Copy report to temp
cp target/cucumber-report.html /tmp/overview-features.html
echo "✅ Report copied to temp location"

# Fetch and checkout gh-pages
git fetch origin gh-pages
git checkout gh-pages
git pull origin gh-pages

# Copy report (force overwrite)
cp -f /tmp/overview-features.html ./overview-features.html

# Commit and push
git add overview-features.html
git commit -m "Update Cucumber test report - $(date +%Y-%m-%d-%H-%M)" || echo "No changes"
git push origin gh-pages

# Return to original branch
git checkout $CURRENT_BRANCH

echo "✅ Report published!"
echo "📄 View at: https://pinolopez.github.io/java/overview-features.html"
