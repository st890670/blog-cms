
import { LOGIN_ROUTE_CONFIG } from 'page/login/config'
import { ARTICLE_ROUTE_CONFIG, ARTICLE_ADD_ROUTE_CONFIG, ARTICLE_EDIT_ROUTE_CONFIG } from 'page/article/config'
import { MANAGEMENT_ROUTE_CONFIG } from 'page/management/config'
import { ERROR_404_ROUTE_CONFIG, ERROR_500_ROUTE_CONFIG } from 'page/error/config'

import Login from 'page/login'
import Article from 'page/article'
import ArticleAdd from 'page/article/add'
import ArticleEdit from 'page/article/edit'
import Management from 'page/management'
import Error404 from 'page/error/404'
import Error500 from 'page/error/500'

const LOGIN_ROUTE = { ...LOGIN_ROUTE_CONFIG, component: Login }
const ARTICLE_ROUTE = { ...ARTICLE_ROUTE_CONFIG, component: Article }
const ARTICLE_ADD_ROUTE = { ...ARTICLE_ADD_ROUTE_CONFIG, component: ArticleAdd }
const ARTICLE_EDIT_ROUTE = { ...ARTICLE_EDIT_ROUTE_CONFIG, component: ArticleEdit }
const MANAGEMENT_ROUTE = { ...MANAGEMENT_ROUTE_CONFIG, component: Management }
const ERROR_404_ROUTE = { ...ERROR_404_ROUTE_CONFIG, component: Error404 }
const ERROR_500_ROUTE = { ...ERROR_500_ROUTE_CONFIG, component: Error500 }

export const OPEN_LAYOUT = [LOGIN_ROUTE, ERROR_404_ROUTE, ERROR_500_ROUTE]
export const LOGGED_IN_LAYOUT = [ARTICLE_ROUTE, ARTICLE_ADD_ROUTE, ARTICLE_EDIT_ROUTE, MANAGEMENT_ROUTE]
