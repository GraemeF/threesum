(ns threesum.core)

(def board [[nil nil  3  nil]
            [ 3   2  nil nil]
            [nil  1   3   3 ]
            [ 1  nil  2   1 ]])

(defn format-row [row]
  (map #(if (nil? %)
          "    "
          (format "%04d" %))
       row))

(defn print-board [board]
  (doseq [row board]
    (println (format-row row))))

;;(print-board board)

(defn -main []
  (print-board board))

