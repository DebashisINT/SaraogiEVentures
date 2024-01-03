package com.ewentteamfsm.features.newcollection.model

import com.ewentteamfsm.app.domain.CollectionDetailsEntity
import com.ewentteamfsm.base.BaseResponse
import com.ewentteamfsm.features.shopdetail.presentation.model.collectionlist.CollectionListDataModel

/**
 * Created by Saikat on 15-02-2019.
 */
class NewCollectionListResponseModel : BaseResponse() {
    //var collection_list: ArrayList<CollectionListDataModel>? = null
    var collection_list: ArrayList<CollectionDetailsEntity>? = null
}