# Robust PowerShell Runner for Smart Car Safety & Control System
$jdkPaths = @(
    "$env:JAVA_HOME\bin\javac.exe",
    "C:\Program Files\Java\jdk-26.0.1\bin\javac.exe",
    (Get-ChildItem -Path "C:\Program Files\Java" -Filter "javac.exe" -Recurse -ErrorAction SilentlyContinue | Select-Object -ExpandProperty FullName -First 1)
)

$javacCmd = $jdkPaths | Where-Object { $_ -and (Test-Path $_) } | Select-Object -First 1
if (-not $javacCmd) { $javacCmd = "javac" }

$javaCmd = $javacCmd -replace "javac", "java"
if (-not (Test-Path $javaCmd)) { $javaCmd = "java" }

Write-Host "[1/2] Compiling Smart Car System using: $javacCmd" -ForegroundColor Cyan
if (!(Test-Path "bin")) { New-Item -ItemType Directory -Path "bin" | Out-Null }

$files = (Get-ChildItem -Path "src" -Recurse -Filter "*.java").FullName
$files | Out-File -Encoding ascii sources.txt
& $javacCmd -d bin "@sources.txt"

if ($LASTEXITCODE -eq 0) {
    Write-Host "[2/2] Launching Smart Car Simulator..." -ForegroundColor Green
    & $javaCmd -cp bin com.smartcar.main.Main
} else {
    Write-Host "[ERROR] Compilation failed!" -ForegroundColor Red
}
