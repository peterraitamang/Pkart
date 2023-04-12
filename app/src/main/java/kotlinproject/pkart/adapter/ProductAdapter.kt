package kotlinproject.pkart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinproject.pkart.databinding.LayoutProductItemBinding
import kotlinproject.pkart.model.AddProductModel

class ProductAdapter (val context: Context, val list: ArrayList<AddProductModel>)
    :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    inner class ProductViewHolder(val binding: LayoutProductItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.productItemImg)
        holder.binding.productItemTxt1.text = data.productName
        holder.binding.productItemTxt2.text = data.productCategory
        holder.binding.productItemTxt3.text = data.productMrp
        holder.binding.productItemBtn1.text = data.productSp


    }
}