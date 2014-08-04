(ns liberator-tutorial.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :refer [run-jetty]]      
            [compojure.core :refer [defroutes ANY]]))

(defresource create! [params auth]
  :allowed-methods [:post]
  :available-media-types ["application/json"]
  :authorized? (logged-in? auth)

  :malformed? (validator params (:create validations/post))
  :handle-malformed errors-in-ctx

  :post! (create-content ts/create-post params auth record)
  :handle-created record-in-ctx)

(defn main [ctx]
  (format "<html>It's %d milliseconds since the beginning of the epoch."
                                                (System/currentTimeMillis)))

(defroutes app
  (ANY "/foo" [] (resource :available-media-types ["text/html"]
                           :handle-ok main)))

(def handler 
  (-> app 
      (wrap-params)))  
  
(run-jetty #'handler {:port 3000})
