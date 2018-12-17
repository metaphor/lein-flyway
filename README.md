# lein-flyway
[![CircleCI](https://circleci.com/gh/metaphor/lein-flyway.svg?style=shield)](https://circleci.com/gh/metaphor/lein-flyway)

## Intro

Leiningen plugin for database migration tool [flyway](http://flywaydb.org/).

This plugin is alpha version, expecting defects.

## Usage

### Latest stable version

[![Clojars Project](http://clojars.org/com.github.metaphor/lein-flyway/latest-version.svg)](http://clojars.org/com.github.metaphor/lein-flyway)

aligned with flyway version ```5.2.4+```

### Configuration
lein-flyway tries to align with offical configuration properties, please refer to [sample.project.clj](https://github.com/metaphor/lein-flyway/blob/master/sample.project.clj) for configuration details.

system variable has been supported to override configuration listed in *sample.project.clj*, you could follow as beblow:

```
url=jdbc:h2:file:/data/sample user=db_user password=secret lein flyway ${subtask}
```

### Leiningen Tasks


```shell
lein flyway ${subtask}
```
And *subtask* includes following flyway aligned tasks,

* clean
* validate
* migrate
* baseline
* repair
* info

### Contributors

See [Github contributors](https://github.com/metaphor/lein-flyway/graphs/contributors)

## LICENSE

Copyright © Zhiheng Li

Licensed under the EPL (see the file LICENSE)
