(defproject xurl-linker "0.2.0"
  :description "A tool for linking macOS applications together by leveraging Clojure and the x-callback-url protocol."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]]
  :main xurl-linker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
