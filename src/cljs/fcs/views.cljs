(ns fcs.views
  (:require
   [re-frame.core :as rf]
   [markdown.core :refer [md->html]]))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href   uri
    :class (when (= page @(rf/subscribe [:common/page])) :is-active)}
   title])

(defn navbar []
  [:nav.navbar.is-info>div.container
   [:div.navbar-brand
    [:a.navbar-item {:href "/" :style {:font-weight :bold}} "App"]]
   [:div#nav-menu.navbar-menu
    [:div.navbar-start
     [nav-link "#/" "Home" :home]
     [nav-link "#/about" "About" :about]]]])

(defn about-page []
  [:section.section>div.container>div.content
   [:img {:src "/img/warning_clojure.png"}]])

(defn home-page []
  [:section.section>div.container>div.content
   (when-let [docs @(rf/subscribe [:page-content])]
     [:div {:dangerouslySetInnerHTML {:__html (md->html docs)}}])])

(defn error-page []
  (when-let [error @(rf/subscribe [:common/error])]
    [:section.section>div.container>div.content
     [:div {:dangerouslySetInnerHTML {:__html error}}]]))

(defn page []
  (if-let [page @(rf/subscribe [:common/page])]
    [:div
     [navbar]
     [page]]))
