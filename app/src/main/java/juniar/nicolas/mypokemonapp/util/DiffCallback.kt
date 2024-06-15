package juniar.nicolas.mypokemonapp.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DiffCallback : DiffUtil.Callback() {

    private var oldList: List<Any> = emptyList()
    private var newList: List<Any> = emptyList()

    fun setList(oldList: List<Any>, newList: List<Any>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> MutableList<T>.refreshDiff(
    diffCallback: DiffCallback,
    oldList: List<Any>,
    newData: List<Any>,
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) {
    diffCallback.setList(oldList, newData)
    val result = DiffUtil.calculateDiff(diffCallback)
    clear()
    newData.map {
        add(it as T)
    }
    result.dispatchUpdatesTo(adapter)
}