(ns xurl-linker.gui.main
  (:require [xurl-linker.gui.components.bear :as bear]
            [fn-fx.fx-dom :as dom]
            [fn-fx.controls :as ui]
            [fn-fx.diff :refer [component defui render should-update?]]))

;; global styling
(def colors {:firebrick (ui/color :red 0.69 :green 0.13 :blue 0.13)})

(def dimensions {:height 200
                 :width 400})

;; root stage
(defui Stage
  (render [this args]
          (ui/stage
            :title "Title!"
            :shown true
            :min-width (:width dimensions)
            :min-height (:height dimensions)
            :scene (ui/scene
                      :root (bear/add-note args)))))

;; data
(def data-state (atom {:status-text ""}))

;; event dispatch
(defmulti dispatch-event :event)
(defmethod dispatch-event :auth
  [{:keys [event] :as event-data}]
  (println event-data)
  (swap! data-state assoc :status-text "Added bear note. The link was copied to your clipboard!"))

(defn -main [data-state]
  (let [ui-state (agent (dom/app (stage @data-state)
                                 dispatch-event))
        update-ui (fn [old-ui]
                    (dom/update-app old-ui (stage @data-state)))]
    (add-watch data-state
               :ui
               (fn [_ _ _ _] (send ui-state update-ui)))))

(comment
  (-main data-state)
)
