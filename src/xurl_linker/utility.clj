(ns xurl-linker.utility
  (:import (java.net URLEncoder))
  (:require [clojure.string :as s])
  (:gen-class))

(defn xurl-encode
  [s]
  (-> s
      (URLEncoder/encode)
      (.replace "+" "%20")))

(defn str-entry
  "Takes a map entry [a b] and produces a new entry containing url-encoded
   string representations of a and b. It uses the name of a."
  [[a b]]
  (map xurl-encode
       [(name a) b]))

(defn param-string
  "Takes a map and creates a url param string from all the key-value entries"
  [mp]
  (if-let [m mp]
    (let [str-pairs (map str-entry m)
          url-params (map #(s/join "=" %) str-pairs)]
      (reduce #(s/join "&" [%1 %2]) url-params))
    ""))

(defn get-clipboard
  []
  (let [toolkit (java.awt.Toolkit/getDefaultToolkit)]
    (.getSystemClipboard toolkit)))

(defn set-clipboard-contents!
  "Sets string s as the contents of the system's clipboard"
  [s]
  (let [selection (new java.awt.datatransfer.StringSelection s)
        clipboard (get-clipboard)]
    (.setContents clipboard selection selection)))

(defn get-clipboard-contents!
  "Returns the current contents of the system clipboard as a string"
  []
  (let [clipboard (get-clipboard)
        contents (.getData clipboard java.awt.datatransfer.DataFlavor/stringFlavor)]
    contents))
