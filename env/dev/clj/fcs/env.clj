(ns fcs.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [fcs.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[fcs started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[fcs has shut down successfully]=-"))
   :middleware wrap-dev})
