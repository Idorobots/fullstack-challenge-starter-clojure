(ns fcs.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[fcs started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[fcs has shut down successfully]=-"))
   :middleware identity})
