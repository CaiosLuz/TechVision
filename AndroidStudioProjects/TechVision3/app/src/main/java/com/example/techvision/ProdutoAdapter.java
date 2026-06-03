package com.example.techvision;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.target.Target;
import com.example.techvision.model.Produto;

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
        com.example.techvision.model.Produto p = produtos.get(position);

        holder.nome.setText(p.getNome());
        holder.preco.setText(String.format("R$ %.2f", p.getPreco()));

        if (p.getImagemUrl() != null && !p.getImagemUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(p.getImagemUrl())
                    .listener(new com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable com.bumptech.glide.load.engine.GlideException e, Object model, com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target, boolean isFirstResource) {
                            Log.e("GLIDE_ERROR", "Erro ao carregar: " + e.getMessage());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.foto);
        } else {
            holder.foto.setImageResource(R.mipmap.lente_normal_round);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(p));
    }

    // Adicione este método para atualizar a lista quando a API responder
    public void atualizarLista(List<com.example.techvision.model.Produto> novaLista) {
        this.produtos = novaLista;
        notifyDataSetChanged();
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