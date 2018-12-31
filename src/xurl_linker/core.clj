(ns xurl-linker.core
  (:require [xurl-linker.xurl :as xurl])
  (:gen-class))

  ;; todo: if action-params contains a key val where val is a vector,
  ;; it needs to automatically create a url string from that vector
(defn make-note!
  [{:keys [title] :as params}]
  (let [{:keys [identifier]} (xurl/call :bear
                                        :x-callback-url
                                        :create
                                        {:action-params params})]
    {:id identifier}))

(defn link-to-note
  [note-id]
  (xurl/make-xurl :bear
                  :x-callback-url
                  :open-note
                  {:action-params {:id note-id}}))

;; error handling dispatch
(defmulti handle-error :type)
(defmethod handle-error :x-error
  [{:keys [type domain code message] :as err}]
  (clojure.pprint/print-table [err]))
(defmethod handle-error :default
  [err]
  (prn "No handler set for this exception")
  (throw err))

;; main method for cmd-line execution
(defn -main
  [& args]
  (try
    (let [note-id (:id (make-note!
                        {:title (first args)}))
          link (link-to-note note-id)]
      (println link)
      (System/exit 0)
      link)
    (catch Exception e
      (handle-error (ex-data e))
      (System/exit 1))))


(link-to-note "0BA6718C-AACD-44AC-82D7-014D85F5BC3B-782-000082CDA6582238")
