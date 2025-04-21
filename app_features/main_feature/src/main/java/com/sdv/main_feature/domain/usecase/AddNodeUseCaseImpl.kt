package com.sdv.main_feature.domain.usecase

import com.sdv.main_feature.data.repository.MainRepository
import com.sdv.main_feature.domain.model.NodeUI
import kotlinx.coroutines.flow.first
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

internal class AddNodeUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) :AddNodeUseCase {

    override suspend fun invoke(nodeUI: NodeUI) {
        //вставка ребенка
        val newNodeUI = NodeUI(
            name = encrypt(mainRepository.getMaxId().first().toString()),
            idParent = nodeUI.id,
            parents = nodeUI.parents,
            children = emptyList()
        )
        val newNodeUIid = mainRepository.insert(newNodeUI)
        // доб нового ребенка в children у родителя
        val temp:MutableList<Long> = mutableListOf()
        temp.addAll(nodeUI.children)
        temp.add(newNodeUIid)
        val oldNodeUI = NodeUI(
            id = nodeUI.id,
            name = nodeUI.name,
            idParent = nodeUI.idParent,
            parents = nodeUI.parents,
            children = temp.toList()
        )
        mainRepository.insert(oldNodeUI)
    }

    private fun encrypt(input: String): String {
        val md = MessageDigest.getInstance("SHA-1")
        val messageDigest = md.digest(input.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashText = no.toString(16)
        while (hashText.length < 20) {
            hashText = "0$hashText"
        }
        return hashText
    }
}