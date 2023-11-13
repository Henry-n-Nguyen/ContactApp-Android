package com.example.contactapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var items : ArrayList<Item>
    lateinit var adapter : MyAdapter
    lateinit var detailView : ConstraintLayout
    lateinit var contactView : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactView = findViewById(R.id.contactView)

        detailView = findViewById(R.id.detailView)

        recyclerView = findViewById(R.id.recyclerView)

        items = Constants.getData()
        adapter = MyAdapter(applicationContext, items)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setOnClickListener( object : MyAdapter.OnClickListener {
            override fun onClick(position: Int, model: Item) {
                detailView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                contactView.visibility = View.GONE

                val backBtn : ImageButton = findViewById(R.id.back_btn)
                backBtn.setOnClickListener {
                    detailView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }

                val image: ImageView = findViewById(R.id.detail_imageview)
                val name: TextView = findViewById(R.id.detail_name)
                val number: TextView = findViewById(R.id.detail_number)
                val idText : TextView = findViewById(R.id.detail_id_text)
                val id: TextView = findViewById(R.id.detail_id_content)
                val emailText: TextView = findViewById(R.id.detail_email_text)
                val email: TextView = findViewById(R.id.detail_email_content)

                idText.text = "ID"
                emailText.text = "Email"

                image.setImageResource(model.image)
                name.text = model.name
                number.text = model.number
                id.text = model.id
                email.text = model.email
            }
        })

        adapter.setOnLongClickListener( object : MyAdapter.OnLongClickListener {
            override fun onLongClick(position: Int, model: Item) {
                contactView.visibility = View.VISIBLE

                val closeBtn : ImageButton = findViewById(R.id.close_btn)
                closeBtn.setOnClickListener {
                    contactView.visibility = View.GONE
                }

                val name: TextView = findViewById(R.id.contact_name)
                name.text = model.name
            }
        })
    }
}

class Item() {

    var image: Int = 0
    lateinit var name: String
    lateinit var number: String
    lateinit var id: String
    lateinit var email: String

    constructor(image: Int, name: String, number: String, id: String, email: String) : this() {
        this.image = image
        this.name = name
        this.number = number
        this.id = id
        this.email = email
    }
}

object Constants {
    fun getData():ArrayList<Item>{
        // create an arraylist of type employee class
        val items = ArrayList<Item>()

        items.add(Item(R.drawable.a, "Name 01", "0123456789", "001", "example.example@gmail.com"))
        items.add(Item(R.drawable.b, "Name 02", "0123456789", "002", "example.example@gmail.com"))
        items.add(Item(R.drawable.c, "Name 03", "0123456789", "003", "example.example@gmail.com"))
        items.add(Item(R.drawable.d, "Name 04", "0123456789", "004", "example.example@gmail.com"))
        items.add(Item(R.drawable.e, "Name 05", "0123456789", "005", "example.example@gmail.com"))
        items.add(Item(R.drawable.f, "Name 06", "0123456789", "006", "example.example@gmail.com"))

        return  items
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: ImageView
    var nameView: TextView

    init {
        imageView = itemView.findViewById(R.id.imageview)
        nameView = itemView.findViewById(R.id.name)
    }
}

class MyAdapter() : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var items: List<Item>
    private lateinit var context: Context

    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener? = null

    constructor(context: Context, items: List<Item>) : this() {
        this.context = context
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.number_intro, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameView.text = items[position].name
        holder.imageView.setImageResource(items[position].image)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, items[position])
            }
        }

        holder.itemView.setOnLongClickListener {
            if (onLongClickListener != null) {
                onLongClickListener!!.onLongClick(position, items[position])
            }
            true
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setOnLongClickListener(onLongClickListener: OnLongClickListener) {
        this.onLongClickListener = onLongClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Item)
    }

    interface OnLongClickListener {
        fun onLongClick(position: Int, model: Item)
    }
}
