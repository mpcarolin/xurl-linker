(ns xurl-linker.xurl
  (:use [clojure.java.shell :only [sh]])
  (:require [xurl-linker.utility :as util]
            [clojure.data.json :as json])
  (:gen-class))

(defn make-xurl
  [scheme host action {:keys [action-params callback-params]}]
  (let [action-param-str (util/param-string action-params)
        callback-param-str (util/param-string callback-params)
        components (map name [host action])
        scheme-str (str (name scheme) "://")
        root (str scheme-str (clojure.string/join "/" components))]
      (str root "?"
           action-param-str "&"
           callback-param-str)))


(def xcall-path "resources/xcall.app/Contents/MacOS/xcall")

(defn failed?
  [x]
  (if (string? x)
    (= 1 (Integer/parseInt x))
    (= 1 x)))

;; is this at all unique to Bear?
(defn xcall-error
  "Crafts an error object out of the err string from the Bear app."
  [err]
  (let [{:keys [errorDomain
                error-Code
                errorMessage]} (json/read-str err :key-fn keyword)]
    (ex-info
      errorMessage
      {:type :x-error
       :message errorMessage
       :code error-Code
       :domain errorDomain})))

(defn xcall
  "Executes the x-callback-url via the commandline."
  [url]
  (sh xcall-path "-url" url))
(xcall "bear://x-callback-url/create?title=Work%20dammit&")
(defn call
  "Calls the x-url-callback defined by scheme, host, action, and params.
   On x-error, call will throw an exception, with :type :x-error in the ex-info map.
   Otherwise, if it is successful, it will return the x-success data as a map."
  [scheme host action params]
  (let [url (make-xurl scheme host action params)
        {:keys [exit err out]} (xcall url)]
    (if (failed? exit)
      (throw (xcall-error err))
      (json/read-str out :key-fn keyword))))

(comment sample execution
(call :bear
      :x-callback-url
      :create
      {:action-params {:title "Work dammit"}})
)
