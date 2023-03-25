# Validator

![Foreman Logo](/assets/play-store.png)

[![Maven Central](https://img.shields.io/maven-central/v/by.shostko/validator?style=flat)](#integration) [![API-level](https://img.shields.io/badge/API-21+-blue?style=flat&logo=android)](https://source.android.com/setup/start/build-numbers) [![License](https://img.shields.io/badge/license-Apach%202.0-green?style=flat)](#license)

## How to use

// TBD

## Integration

The library is available in Maven Central repository:
```gradle
dependencies {
    implementation 'by.shostko:validator:0.+'
}
```

You can also use collections of ready-to-use validators:
```gradle
dependencies {
    implementation 'by.shostko:validator-exceptions:0.+' // result is Exception
    implementation 'by.shostko:validator-strings:0.+'    // result is pure String
    implementation 'by.shostko:validator-android:0.+'    // result is android resource id (Int) 
}
```

### License

Released under the [Apache 2.0 license](LICENSE).

```
Copyright 2019 Sergey Shostko

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
