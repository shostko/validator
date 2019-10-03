# RxValidation

[ ![Download](https://api.bintray.com/packages/shostko/android/rx-validation/images/download.svg) ](https://bintray.com/shostko/android/rx-validation/_latestVersion)
Wraps and helps working with validations in reactive streams

## Integration

As soon as it is still in development you should add to your project Gradle configuration:

```gradle
repositories {
    maven { url "https://dl.bintray.com/shostko/android" }
}
```

Base module integration:
```gradle
dependencies {
    implementation 'by.shostko:rx-validation:0.+'

    // ADD
    // collection of implemented validators that throws simple Exception
    implementation 'by.shostko:rx-validation-validators:0.+'
    // OR
    // collection of implemented validators that throws Errors
    // see https://github.com/shostko/errors
    implementation 'by.shostko:rx-validation-errors:0.+'
}
```

Also don't forget to additional mandatory dependencies:
```gradle
dependencies {
    // for base module
    implementation 'io.reactivex.rxjava2:rxjava:2.+'

    // for errors module integration
    implementation 'by.shostko:error:0.+'
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