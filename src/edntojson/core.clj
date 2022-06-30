(ns edntojson.core
  (:gen-class))
(require
 '[clojure.java.io :as io]
 '[clojure.edn :as edn]
 '[clojure.data.json :as json])

;; method to filter out non files from args
(defn create-files-from-args
  [posible_file_name]
  (let
   [posible_file (io/file posible_file_name)]
    (if (.exists posible_file) posible_file nil)))

(defn -main
  [& args]

  ;; filter out non files
  (def input-files (remove nil? (map create-files-from-args args)))

  ;; defire regex keys
  (def key-pattern (re-pattern "(:[^\\/]*)\\/([^\\/][^\\s]*)"))
  (def edn-pattern (re-pattern ".edn$"))

  (doseq
   [input-file  input-files]

    (let
     ;; read in file
     [edn-data
      (edn/read
       (java.io.PushbackReader.
        (io/reader
         (.getBytes
          (clojure.string/replace (slurp input-file) key-pattern "$1_$2")))))
      ;; create output file
      out-file
      (clojure.java.io/file
       (.getParent input-file)
       (clojure.string/replace (.getName input-file) edn-pattern ".json"))]

      ;; send data to output file
      (spit out-file  (json/write-str  edn-data))
      (println (.toString out-file)))))