package com.example.techvision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    private List<Produto> produtos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Produto produto);
    }

    public ProdutoAdapter(List<Produto> produtos, OnItemClickListener listener) {
        this.produtos = produtos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Certifique-se de criar o arquivo item_produto.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto p = produtos.get(position);
        holder.nome.setText(p.getNome());
        holder.preco.setText(p.getPreco());
        holder.foto.setImageResource(p.getImagemRes());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nome, preco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.imgItemProduto);
            nome = itemView.findViewById(R.id.txtItemNome);
            preco = itemView.findViewById(R.id.txtItemPreco);
        }
    }
}