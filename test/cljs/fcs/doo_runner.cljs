(ns fcs.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [fcs.core-test]))

(doo-tests 'fcs.core-test)
