import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '设备编号',
    align:"center",
    dataIndex: 'equipmentCode'
   },
   {
    title: '设备名称',
    align:"center",
    dataIndex: 'equipmentName'
   },
   {
    title: '设备状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '所属组',
    align:"center",
    dataIndex: 'groupId_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '设备编号',
    field: 'equipmentCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入设备编号!'},
          ];
     },
  },
  {
    label: '设备名称',
    field: 'equipmentName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入设备名称!'},
          ];
     },
  },
  {
    label: '设备状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"equipment_status",
     },
  },
  {
    label: '所属组',
    field: 'groupId',
    component: 'JSearchSelect',
    componentProps:{
       dict:"lab_group,mentor,group_id"
    },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  equipmentCode: {title: '设备编号',order: 0,view: 'text', type: 'string',},
  equipmentName: {title: '设备名称',order: 1,view: 'text', type: 'string',},
  status: {title: '设备状态',order: 2,view: 'list', type: 'string',dictCode: 'equipment_status',},
  groupId: {title: '所属组',order: 3,view: 'sel_search', type: 'string',dictTable: "lab_group", dictCode: 'group_id', dictText: 'mentor',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}