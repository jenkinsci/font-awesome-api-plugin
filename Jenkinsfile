def configurations = [
  [ platform: "linux", jdk: "21" ],
  [ platform: "windows", jdk: "25" ]
]

buildPlugin(failFast: false, configurations: configurations,
    spotbugs: [filters: [excludeType('NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE')]],
    checkstyle: [qualityGates: [[threshold: 1, type: 'NEW', unstable: true]]],
    pmd: [qualityGates: [[threshold: 1, type: 'NEW', unstable: true]]] )
