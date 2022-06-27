(ns edntojson.core
  (:gen-class))
(require
 '[clojure.java.io :as io]
 '[clojure.edn :as edn]
 '[clojure.data.json :as json])

(def output-dir (clojure.java.io/file "output_files"))
(def input-dir (clojure.java.io/file "input_files"))

;; (def countryedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../country.edn"))))))
;; (def adx_datasetedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../adx-dataset.edn"))))))
;; (def categoryedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../category.edn"))))))
;; (def platformedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../platform.edn"))))))
;; (def taxonomyedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../taxonomy.edn"))))))
;; (def taxonomyNodeedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../taxonomyNode.edn"))))))

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

  (println "here 1")
  (println input-files)

  (doseq
  ;;  [input-file (take 1 (nthnext input-files 4))]
   [input-file  input-files]

    (let
     [edn-data (edn/read (java.io.PushbackReader. (io/reader (.getBytes (clojure.string/replace (slurp input-file) key-pattern "$1_$2")))))
      out-file (clojure.java.io/file (.getParent input-file) (clojure.string/replace (.getName input-file) edn-pattern ".json"))]
    ;;  (println (json/write-str  edn-data))
      (spit out-file  (json/write-str  edn-data))))

;; (println  (file-seq input-dir))
  ;; (io/reader (.getBytes (slurp "../country.edn")))

  ;; (spit "../country.json"  (json/write-str  countryedn))
  ;; (spit "../adx_dataset.json"  (json/write-str  adx_datasetedn))
  ;; (spit "../category.json"  (json/write-str  categoryedn))
  ;; (spit "../platform.json"  (json/write-str  platformedn))
  ;; (spit "../taxonomy.json"  (json/write-str  taxonomyedn))
  ;; (spit "../taxonomyNode.json"  (json/write-str  taxonomyNodeedn))
  )
;; (ns edntojson.core
;;   (:gen-class))
;; (require
;;  '[clojure.java.io :as io]
;;  '[clojure.edn :as edn]
;;  '[clojure.data.json :as json])

;; (defn writelines [file-path lines]
;;   (with-open [wtr (clojure.java.io/writer file-path)]
;;     (doseq [line lines] (.write wtr line))))

;; ;; (def countryedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../country.edn"))))))
;; ;; (def adx_datasetedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../adx-dataset.edn"))))))
;; ;; (def categoryedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../category.edn"))))))
;; ;; (def platformedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../platform.edn"))))))
;; ;; (def taxonomyedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../taxonomy.edn"))))))
;; ;; (def taxonomyNodeedn (edn/read (java.io.PushbackReader. (io/reader (.getBytes (slurp "../taxonomyNode.edn"))))))

;; (defn create-files-from-args
;;   [posible_file_name]
;;   (let
;;    [posible_file (io/file posible_file_name)]
;;     (if (.exists posible_file) posible_file nil)))

;; (defn -main
;;   [& args]

;;   (def input-files (remove nil? (map create-files-from-args args)))

;; ;; (println (next (file-seq input-dir)))
;;   ;; (println (slurp thing-file))

;;     ;; (println (slurp (take 1 (next (file-seq input-dir)))))

;;   ;; (doseq
;;   ;;  [input-file (take 1 (nthnext  (file-seq input-dir) 4))]
;;   ;;   (let
;;   ;;    [file-data ]
;;   ;;     (println (.getName input-file))))

;;   ;; (println  (file-seq input-dir))
;;   ;; (io/reader (.getBytes (slurp "../country.edn")))

;;   ;; (spit "../country.json"  (json/write-str  countryedn))
;;   ;; (spit "../adx_dataset.json"  (json/write-str  adx_datasetedn))
;;   ;; (spit "../category.json"  (json/write-str  categoryedn))
;;   ;; (spit "../platform.json"  (json/write-str  platformedn))
;;   ;; (spit "../taxonomy.json"  (json/write-str  taxonomyedn))
;;   ;; (spit "../taxonomyNode.json"  (json/write-str  taxonomyNodeedn))
;;   )
