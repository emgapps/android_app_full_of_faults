# FullOfFaultsApp

Small Android sample app for generating native crashes and sending them to Backtrace.

## What it does

The app shows a few buttons that intentionally trigger native faults such as:

- `SIGSEGV` from a null pointer write
- `SIGSEGV` from a stack overflow
- `SIGABRT`

This is useful for validating Backtrace crash reporting in a local test app.

## Local setup

Create or update `local.properties` in the project root. The app build requires a Backtrace submission URL.

```properties
sdk.dir=/path/to/Android/sdk
backtraceSubmissionUrl=https://submit.backtrace.io/<your-universe>/<your-token>/json
```

Notes:

- `local.properties` is ignored by git and should stay local to your machine.
- Replace `backtraceSubmissionUrl` with the submission URL from your Backtrace project.
- The build will fail if `backtraceSubmissionUrl` is missing.

## Run

Open the project in Android Studio and run the `app` configuration on an emulator or device.

You can also build from the command line:

```bash
./gradlew assembleDebug
```
