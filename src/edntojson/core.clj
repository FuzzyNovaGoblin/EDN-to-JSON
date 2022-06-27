(ns edntojson.core
  (:gen-class))
(require
 '[clojure.java.io :as io]
 '[clojure.edn :as edn]
 '[clojure.data.json :as json])

(def output-dir (clojure.java.io/file "output_files"))
(def input-dir (clojure.java.io/file "input_files"))

(defn create-files-from-args
  [posible_file_name]
  (let
   [posible_file (io/file posible_file_name)]
    (if (.exists posible_file) posible_file nil)))

(defn -main
  [& args]

  (def input-files (remove nil? (map create-files-from-args args)))

  (def key-pattern (re-pattern "(:[^\\/]*)\\/([^\\s]*)"))
  (def edn-pattern (re-pattern ".edn$"))

  (doseq
   [input-file  input-files]

    (let
     [edn-data (edn/read (java.io.PushbackReader. (io/reader (.getBytes (clojure.string/replace (slurp input-file) key-pattern "$1_$2")))))
      out-file (clojure.java.io/file (.getParent input-file) (clojure.string/replace (.getName input-file) edn-pattern ".json"))]
      (spit out-file  (json/write-str  edn-data)))))