package com.example.techvision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.techvision.model.Produto;
import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    private List<Produto> itensCarrinho;
    private OnCarrinhoChangeListener listener;

    // Interface para comunicar mudanças (como remover item ou atualizar total)
    public interface OnCarrinhoChangeListener {
        void onItemRemovido(int posicao);
    }

    public CarrinhoAdapter(List<Produto> itensCarrinho, OnCarrinhoChangeListener listener) {
        this.itensCarrinho = itensCarrinho;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto p = itensCarrinho.get(position);

        holder.txtNome.setText(p.getNome());
        holder.txtPreco.setText(String.format("R$ %.2f", p.getPreco()));

        // Se a descrição contiver o tipo de lente, exibimos aqui
        holder.txtDescricao.setText(p.getDescricao());

        // Carregar imagem com Glide
        if (p.getImagemUrl() != null && !p.getImagemUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(p.getImagemUrl())
                    .placeholder(R.mipmap.lente_normal_round)
                    .into(holder.imgProduto);
        } else {
            holder.imgProduto.setImageResource(R.mipmap.lente_normal_round);
        }

        // Configurar o botão de remover (lixeira)
        holder.btnRemover.setOnClickListener(v -> {
            if (listener != null) {
                // Use getAdapterPosition() em vez de 'position' para garantir que
                // a posição esteja correta mesmo após outras remoções
                int currentPos = holder.getAdapterPosition();
                if (currentPos != RecyclerView.NO_POSITION) {
                    listener.onItemRemovido(currentPos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itensCarrinho.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto;
        TextView txtNome, txtPreco, txtDescricao;
        ImageButton btnRemover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgCarrinhoProduto);
            txtNome = itemView.findViewById(R.id.txtCarrinhoNome);
            txtPreco = itemView.findViewById(R.id.txtCarrinhoPreco);
            txtDescricao = itemView.findViewById(R.id.txtCarrinhoDescricao);
            btnRemover = itemView.findViewById(R.id.btnRemoverItem);
        }
    }
}