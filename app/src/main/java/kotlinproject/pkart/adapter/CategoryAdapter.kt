package kotlinproject.pkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinproject.pkart.CategoryActivity
import kotlinproject.pkart.R
import kotlinproject.pkart.databinding.LayoutCategoryItemBinding
import kotlinproject.pkart.model.CategoryModel


class CategoryAdapter(var context : Context, val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewholder>(){

    inner class CategoryViewholder(view : View) : RecyclerView.ViewHolder(view){
        var binding = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewholder {
        return CategoryViewholder(LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewholder, position: Int) {
        holder.binding.categoryName.text = list[position].cat
        Glide.with(context).load(list[position].img).into(holder.binding.categoryImage)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("cat",list[position].cat)
            context.startActivity(intent)
        }

    }

}