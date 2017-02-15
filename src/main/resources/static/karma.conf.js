// Karma configuration file, see link for more information
// https://karma-runner.github.io/0.13/config/configuration-file.html

module.exports = function (config) {
  var defaultConfig = {
    basePath: '',
    frameworks: ['jasmine', 'angular-cli'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-remap-istanbul'),
      require('angular-cli/plugins/karma'),
      require('karma-junit-reporter'),
      require('karma-phantomjs-launcher')
    ],
    files: [
      { pattern: './src/test.ts', watched: false }
    ],
    preprocessors: {
      './src/test.ts': ['angular-cli']
    },
    mime: {
      'text/x-typescript': ['ts','tsx']
    },
    remapIstanbulReporter: {
      reports: {
        html: 'coverage',
        lcovonly: './coverage/coverage.lcov'
      }
    },
    angularCli: {
      config: './angular-cli.json',
      environment: 'dev'
    },
    reporters: config.angularCli && config.angularCli.codeCoverage
              ? ['progress', 'karma-remap-istanbul']
              : ['progress'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    browsers: ['Chrome'],
    singleRun: true,
    reporters: ['dots']

  };

  console.log("ENV= ", process.env.ENV);
  if(process.env.ENV === 'CI'){
    defaultConfig.singleRun = true;
    defaultConfig.browsers = ['PhantomJS'];
    defaultConfig.reporters.push('junit');
    defaultConfig.junitReporter = {
       outputDir: 'test_results',
       outputFile: 'test-results.xml'
    };
    console.log('CONFIG FOR JENKINS EXECUTION', defaultConfig);
  }

  config.set(defaultConfig);
};
