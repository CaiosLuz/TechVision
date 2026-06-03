package com.example.techvision;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.techvision.model.Pedido;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> listaPedidos;

    public PedidoAdapter(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);

        holder.tvIdPedido.setText("Pedido #" + pedido.getId());
        holder.tvDataPedido.setText("Data: " + pedido.getData());
        holder.tvStatusPedido.setText("Status: " + pedido.getStatus());

        // Se quiser exibir o total formatado, pode descomentar a linha abaixo:
        // holder.tvTotalPedido.setText(String.format("Total: R$ %.2f", pedido.getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaPedidos != null ? listaPedidos.size() : 0;
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPedido, tvDataPedido, tvStatusPedido;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdPedido = itemView.findViewById(R.id.tvIdPedido);
            tvDataPedido = itemView.findViewById(R.id.tvDataPedido);
            tvStatusPedido = itemView.findViewById(R.id.tvStatusPedido);
        }
    }
}