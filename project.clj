(defproject xurl-linker "0.2.0"
  :description "A tool for linking macOS applications together by leveraging Clojure and the x-callback-url protocol."
  :url "https://github.com/mpcarolin/xurl-linker"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]]
  :main xurl-linker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
